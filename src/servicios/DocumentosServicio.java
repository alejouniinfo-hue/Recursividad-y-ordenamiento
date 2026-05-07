package servicios;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import modelos.Documento;

public class DocumentosServicio {

    private static List<Documento> documentos = Collections.emptyList();

    private static String[] encabezados = new String[] {
            "#",
            "Primer Apellido",
            "Segundo Apellido",
            "Nombres",
            "Documento"
    };

    public static String[] getEncabezados() {
        return encabezados;
    }

    public static void cargar(String nombreArchivo) {

        try {

            var lineas = Files.lines(Paths.get(nombreArchivo));

            documentos = lineas
                    .skip(1)
                    .map(linea -> linea.split(";"))
                    .map(partes -> new Documento(
                            partes[0],
                            partes[1],
                            partes[2],
                            partes[3]))
                    .collect(Collectors.toList());

        } catch (Exception e) {

            documentos = Collections.emptyList();
        }
    }

    public static void mostrar(JTable tbl) {

        var datos = documentos.stream()
                .map(documento -> new String[] {
                        String.valueOf(documentos.indexOf(documento) + 1),
                        documento.getApellido1(),
                        documento.getApellido2(),
                        documento.getNombre(),
                        documento.getDocumento()
                })
                .collect(Collectors.toList())
                .toArray(String[][]::new);

        var dtm = new DefaultTableModel(datos, encabezados);

        tbl.setModel(dtm);
    }

    // ==========================================
    // COMPARAR
    // ==========================================

    private static boolean esMayor(
            Documento d1,
            Documento d2,
            int criterio) {

        // Primero por Nombre Completo
        // Luego por Documento

        if (criterio == 0) {

            return d1.getNombreCompleto()
                    .compareTo(d2.getNombreCompleto()) > 0 ||

                    (d1.getNombreCompleto()
                            .equals(d2.getNombreCompleto())

                            &&

                            d1.getDocumento()
                                    .compareTo(d2.getDocumento()) > 0);
        }

        // Primero por Documento
        // Luego por Nombre Completo

        return d1.getDocumento()
                .compareTo(d2.getDocumento()) > 0 ||

                (d1.getDocumento()
                        .equals(d2.getDocumento())

                        &&

                        d1.getNombreCompleto()
                                .compareTo(d2.getNombreCompleto()) > 0);
    }

    // ==========================================
    // INTERCAMBIAR
    // ==========================================

    private static void intercambiar(int i, int j) {

        if (0 <= i &&
                i < documentos.size() &&
                0 <= j &&
                j < documentos.size()) {

            var aux = documentos.get(i);

            documentos.set(i, documentos.get(j));

            documentos.set(j, aux);
        }
    }

    // ==========================================
    // BURBUJA
    // ==========================================

    public static void ordenarBurbuja(int criterio) {

        for (int i = 0; i < documentos.size() - 1; i++) {

            for (int j = i + 1; j < documentos.size(); j++) {

                if (esMayor(
                        documentos.get(i),
                        documentos.get(j),
                        criterio)) {

                    intercambiar(i, j);
                }
            }
        }
    }

    // ==========================================
    // QUICK SORT
    // ==========================================

    private static int getPivote(
            int inicio,
            int fin,
            int criterio) {

        int pivote = inicio;

        var documentoPivote = documentos.get(pivote);

        for (int i = inicio + 1; i <= fin; i++) {

            if (esMayor(
                    documentoPivote,
                    documentos.get(i),
                    criterio)) {

                pivote++;

                if (i != pivote) {

                    intercambiar(i, pivote);
                }
            }
        }

        if (inicio != pivote) {

            intercambiar(inicio, pivote);
        }

        return pivote;
    }

    private static void ordenarRapido(
            int inicio,
            int fin,
            int criterio) {

        if (inicio >= fin) {

            return;

        } else {

            var pivote = getPivote(
                    inicio,
                    fin,
                    criterio);

            ordenarRapido(
                    inicio,
                    pivote - 1,
                    criterio);

            ordenarRapido(
                    pivote + 1,
                    fin,
                    criterio);
        }
    }

    public static void ordenarRapido(int criterio) {

        ordenarRapido(
                0,
                documentos.size() - 1,
                criterio);
    }

    // ==========================================
    // MERGE SORT
    // ==========================================

    public static void ordenarMezcla(int criterio) {

        ordenarMezclaRecursivo(
                0,
                documentos.size() - 1,
                criterio);
    }

    private static void ordenarMezclaRecursivo(
            int izquierda,
            int derecha,
            int criterio) {

        if (izquierda < derecha) {

            int medio = (izquierda + derecha) / 2;

            ordenarMezclaRecursivo(
                    izquierda,
                    medio,
                    criterio);

            ordenarMezclaRecursivo(
                    medio + 1,
                    derecha,
                    criterio);

            mezclar(
                    izquierda,
                    medio,
                    derecha,
                    criterio);
        }
    }

    private static void mezclar(
            int izquierda,
            int medio,
            int derecha,
            int criterio) {

        int n1 = medio - izquierda + 1;
        int n2 = derecha - medio;

        Documento[] izquierdaArray = new Documento[n1];
        Documento[] derechaArray = new Documento[n2];

        // Copiar datos

        for (int i = 0; i < n1; i++) {

            izquierdaArray[i] =
                    documentos.get(izquierda + i);
        }

        for (int j = 0; j < n2; j++) {

            derechaArray[j] =
                    documentos.get(medio + 1 + j);
        }

        int i = 0;
        int j = 0;
        int k = izquierda;

        // Mezclar

        while (i < n1 && j < n2) {

            if (!esMayor(
                    izquierdaArray[i],
                    derechaArray[j],
                    criterio)) {

                documentos.set(k, izquierdaArray[i]);

                i++;

            } else {

                documentos.set(k, derechaArray[j]);

                j++;
            }

            k++;
        }

        // Sobrantes izquierda

        while (i < n1) {

            documentos.set(k, izquierdaArray[i]);

            i++;
            k++;
        }

        // Sobrantes derecha

        while (j < n2) {

            documentos.set(k, derechaArray[j]);

            j++;
            k++;
        }
    }

}
