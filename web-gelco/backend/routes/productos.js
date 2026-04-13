const express = require('express');
const { queryDB } = require('../config/database');

const router = express.Router();

router.get('/', async (req, res) => {
  try {
    const result = await queryDB("SELECT * FROM dbo.productos");
    res.json(result.recordset);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

module.exports = router;