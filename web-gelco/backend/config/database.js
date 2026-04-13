const sql = require('mssql/msnodesqlv8');

const config = {
  connectionString: "Driver={ODBC Driver 18 for SQL Server};Server=localhost\\SQLEXPRESS;Database=GELCO;Trusted_Connection=yes;TrustServerCertificate=yes;"
};

let pool;

async function connectDB() {
  try {
    pool = await sql.connect(config);
    console.log('✅ Conectado a SQL Server (Windows Auth)');
    return pool;
  } catch (err) {
    console.error('❌ Error conexión:', err);
  }
}

async function queryDB(query) {
  try {
    if (!pool) await connectDB();
    return await pool.request().query(query);
  } catch (err) {
    throw err;
  }
}

module.exports = { connectDB, queryDB };