const API = "http://localhost:3001/api";

export const getProductos = async () => {
  const res = await fetch(`${API}/productos`);
  return res.json();
};