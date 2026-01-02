/**
 * Función para generar el PDF del formulario de equipos.
 * Utiliza la librería html2pdf.js
 */
function generarPDFRecibo() {
    // 1. Seleccionamos el contenedor exacto que queremos imprimir
    // 'formularioPapel' es el ID del div blanco con borde negro dentro del modal
    const elemento = document.getElementById('formularioPapel');

    if (!elemento) {
        alert("Error: No se encontró el formulario para generar el PDF.");
        return;
    }

    // 2. Obtenemos el FMO para nombrar el archivo
    const fmo = document.getElementById('modal_fmoEquipo').value || 'SinFMO';
    const fecha = document.getElementById('modal_fecha').value || 'Fecha';
    
    // Limpiamos caracteres que puedan dar error en nombre de archivo
    const nombreArchivo = `Recibo_Equipos_${fmo.trim()}_${fecha}.pdf`;

    // 3. Configuración de html2pdf
    const opciones = {
        margin:       5, // Margen en mm (ajustar si se corta)
        filename:     nombreArchivo,
        image:        { type: 'jpeg', quality: 0.98 }, // Calidad de imagen
        html2canvas:  { 
            scale: 2, // Mayor escala = Mejor calidad pero más peso
            useCORS: true, // Importante si hay imágenes externas
            logging: true,
            letterRendering: true
        },
        jsPDF:        { 
            unit: 'mm', 
            format: 'letter', // Tamaño Carta
            orientation: 'portrait' // Vertical
        }
    };

    // 4. Ejecutar la generación
    // El .save() descarga el archivo automáticamente
    html2pdf().set(opciones).from(elemento).save().catch(err => {
        console.error("Error al generar PDF:", err);
        alert("Ocurrió un error al generar el PDF.");
    });
}