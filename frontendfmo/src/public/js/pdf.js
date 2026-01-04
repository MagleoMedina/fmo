/**
 * Función genérica para exportar PDF (Individual).
 */
function generarPDFRecibo() {
    let element = null;
    let nombreBase = "Documento";
    let identificador = "SinID";
    let fecha = "Fecha";

    // --- 1. DETECCIÓN DEL CONTEXTO (¿Qué vista es?) ---

    // A. CASO EQUIPOS
    const formEquipos = document.getElementById('formularioPapel');
    if (formEquipos) {
        element = formEquipos;
        nombreBase = "Recibo_Equipos";
        // Intentamos obtener el FMO
        const inputFmo = document.getElementById('modal_fmoEquipo');
        if (inputFmo && inputFmo.value) identificador = inputFmo.value;
        
        const inputFecha = document.getElementById('modal_fecha');
        if (inputFecha && inputFecha.value) fecha = inputFecha.value;
    }

    // B. CASO PERIFÉRICOS
    const formPerifericos = document.getElementById('formPerifericosModal');
    if (!element && formPerifericos) {
        element = formPerifericos;
        nombreBase = "Recibo_Perifericos";
        // Intentamos obtener el Serial
        const inputSerial = document.getElementById('modal_fmoSerial');
        if (inputSerial && inputSerial.value) identificador = inputSerial.value;
        
        const inputFecha = document.getElementById('modal_fecha');
        if (inputFecha && inputFecha.value) fecha = inputFecha.value;
    }

    // C. CASO DAET
    const formDaet = document.getElementById('formDaetModal');
    if (!element && formDaet) {
        element = formDaet;
        nombreBase = "Entrega_DAET";
        // En DAET usamos Serial o Solicitud como identificador
        const inputSerial = document.getElementById('modal_fmoSerial');
        const inputSolicitud = document.getElementById('modal_solicitudDAET');
        
        if (inputSerial && inputSerial.value) identificador = inputSerial.value;
        else if (inputSolicitud && inputSolicitud.value) identificador = inputSolicitud.value;
        
        const inputFecha = document.getElementById('modal_fecha');
        if (inputFecha && inputFecha.value) fecha = inputFecha.value;
    }

    // --- 2. VALIDACIÓN ---
    if (!element) {
        alert("Error: No se detectó ningún formulario compatible para generar el PDF.");
        return;
    }

    // Limpieza de caracteres inválidos para nombre de archivo
    identificador = identificador.trim().replace(/[\/\\]/g, '-'); 
    fecha = fecha.trim().replace(/[\/\\]/g, '-');
    const nombreArchivo = `${nombreBase}_${identificador}_${fecha}.pdf`;

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
    html2pdf().set(opciones).from(element).save().catch(err => {
        console.error("Error al generar PDF:", err);
        alert("Ocurrió un error al generar el PDF.");
    });
}
/**
 * Genera un PDF multipágina usando SUSTITUCIÓN VISUAL ESTRICTA.
 */
async function generarPDFMasivo(datos, tipo) {
    // --- CONFIGURACIÓN ---
    const MODO_DEPURACION = false; // CAMBIA A TRUE SI QUIERES VER LOS FORMULARIOS EN PANTALLA
    // ---------------------

    // 1. Validar Templates
    let templateId = '';
    if (tipo === 'equipos') templateId = 'template-equipos-wrapper';
    else if (tipo === 'perifericos') templateId = 'template-perifericos-wrapper';
    else if (tipo === 'daet') templateId = 'template-daet-wrapper';

    const templateSource = document.getElementById(templateId);
    if (!templateSource || !templateSource.firstElementChild) {
        return alert("Error crítico: No se encontraron los templates HTML.");
    }

    // 2. Ocultar la APP y Mostrar Telón
    const appContent = document.querySelector('.wrapper');
    if (appContent) appContent.style.display = 'none';

    // Creamos/Mostramos el telón para que el usuario no vea el salto
    let telon = document.getElementById('pdf-loading-overlay');
    if (!telon) {
        telon = document.createElement('div');
        telon.id = 'pdf-loading-overlay';
        // z-index altísimo para tapar el staging area mientras se monta
        telon.style.cssText = "position:fixed; top:0; left:0; width:100%; height:100%; background:white; z-index: 99999; display:flex; justify-content:center; align-items:center; flex-direction:column;";
        telon.innerHTML = `
            <div class="spinner-border text-primary mb-3" role="status"></div>
            <h4 style="color:#333;">Generando PDF...</h4>
            <div style="color:#666;">Procesando ${datos.length} registros...</div>
        `;
        document.body.appendChild(telon);
    }
    if (!MODO_DEPURACION) telon.style.display = 'flex';

    // 3. Preparar ÁREA DE MONTAJE (Staging Area)
    const stagingArea = document.getElementById('print-staging-area');
    stagingArea.innerHTML = '';
    
    // CONFIGURACIÓN ESTRICTA:
    // Lo hacemos visible y parte del flujo normal (static) para que tenga altura real.
    // Al ocultar .wrapper, esto será lo único en el body.
    stagingArea.style.display = 'block';
    stagingArea.style.position = 'static'; // Importante para que crezca hacia abajo
    stagingArea.style.width = '816px'; // Ancho Carta
    stagingArea.style.margin = '0 auto'; // Centrado
    stagingArea.style.background = 'white';
    stagingArea.style.padding = '20px';

    // 4. RESETEAR SCROLL (CRUCIAL PARA EVITAR PÁGINAS EN BLANCO)
    window.scrollTo(0, 0);

    // 5. Bucle de Clonación
    datos.forEach((registro, index) => {
        const wrapper = document.createElement('div');
        wrapper.className = 'pdf-compacto'; 
        wrapper.style.padding = "3px";
        wrapper.style.backgroundColor = "white"; 
        wrapper.style.marginBottom = "3px";

        const clone = templateSource.firstElementChild.cloneNode(true);
        // Asegurar visibilidad
        clone.style.display = 'block';
        clone.removeAttribute('hidden');
        
        if (tipo === 'equipos') mapearDatosEquipos(clone, registro); 
        else if (tipo === 'perifericos') mapearDatosPerifericos(clone, registro);
        else if (tipo === 'daet') mapearDatosDaet(clone, registro);

        wrapper.appendChild(clone);
        stagingArea.appendChild(wrapper);

        // Salto de página
        if (index < datos.length - 1) {
            const pageBreak = document.createElement('div');
            pageBreak.className = 'html2pdf__page-break';
            pageBreak.style.height = "0px";
            stagingArea.appendChild(pageBreak);
        }
    });

    // 6. ESPERA DE RENDERIZADO
    // Damos tiempo al navegador para calcular layout
    await new Promise(resolve => setTimeout(resolve, 1500));

    // 7. Generar PDF
    const opciones = {
        margin:       5,
        filename:     `Reporte_${tipo}_${new Date().toISOString().slice(0,10)}.pdf`,
        image:        { type: 'jpeg', quality: 0.98 },
        html2canvas:  { 
            scale: 2, 
            logging: true, 
            useCORS: true,
            // Eliminamos windowWidth forzado para evitar conflictos de recorte
            scrollY: 0,
            backgroundColor: '#ffffff'
        },
        jsPDF:        { unit: 'mm', format: 'letter', orientation: 'portrait' }
    };

    if (MODO_DEPURACION) {
        alert("Modo Depuración: Mira la pantalla. ¿Ves los formularios? Si sí, el PDF debería salir bien.");
        // No generamos ni ocultamos nada para que puedas inspeccionar
        return;
    }

    try {
        await html2pdf().set(opciones).from(stagingArea).save();
    } catch (e) {
        console.error("Error html2pdf:", e);
        alert("Error generando PDF: " + e.message);
    } finally {
        // 8. RESTAURAR VISIBILIDAD
        if (!MODO_DEPURACION) {
            stagingArea.innerHTML = ''; 
            stagingArea.style.display = 'none'; 
            
            if (appContent) appContent.style.display = 'flex'; 
            telon.style.display = 'none'; 
        }
    }
}