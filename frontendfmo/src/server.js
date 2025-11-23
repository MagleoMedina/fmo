const express = require('express');
const path = require('path');
const app = express();
const PORT = 3000;

// Configurar EJS como motor de plantillas
app.set('view engine', 'ejs');
app.set('views', path.join(__dirname, 'views'));

// Ruta principal
app.get('/', (req, res) => {
    // Renderiza la vista index.ejs pasando una variable
    res.render('index', { title: 'Hola Mundo desde EJS' });
});

// Iniciar el servidor
const server = app.listen(PORT, () => {
    console.log(`Servidor Express corriendo en http://localhost:${PORT}`);
});

module.exports = server;