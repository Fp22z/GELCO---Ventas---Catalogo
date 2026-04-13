const express = require('express');
const cors = require('cors');
const { connectDB } = require('./config/database');

const app = express();

app.use(cors());
app.use(express.json());

// Ruta
app.use('/api/productos', require('./routes/productos'));

// Iniciar servidor
app.listen(3001, async () => {
  await connectDB();
  console.log('Servidor corriendo en http://localhost:3001');
});