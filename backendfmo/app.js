const express = require('express');
const app = express();
const bodyParser = require('body-parser');
const mongoose = require('mongoose');
const cors = require('cors');

app.use(cors());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

mongoose.connect('mongodb://localhost:27017/fmo', {
  useNewUrlParser: true,
  useUnifiedTopology: true,
});

const usuarioRoutes = require('./routes/usuario');

app.use('/usuarios', usuarioRoutes);

app.listen(3000, () => {
  console.log('Server is running on port 3000');
});