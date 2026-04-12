import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Login.css';

export default function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log('Login:', email, password);
  };

  return (
    <div className="login-container">

      {/* Lado imagen */}
      <div className="login-image">
        <img src="/assets/img-fondo.jpg" alt="Ventas por Catálogo" />
      </div>

      {/* Lado formulario */}
      <div className="login-form-section">
        <div className="form-content">

          <img src="/assets/logo-empresa.png" alt="Logo GELCO" className="logo" />
          <h1>¡Bienvenid@!</h1>
          <p className="login-subtitle">
            Gestiona tus ventas, capacitaciones y pedidos en un solo lugar.
          </p>

          <form onSubmit={handleSubmit}>
            <div className="input-group">
              <label htmlFor="email">Correo Electrónico</label>
              <input
                id="email"
                type="email"
                placeholder="ejemplo@correo.com"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
              />
            </div>

            <div className="input-group">
              <label htmlFor="password">Contraseña</label>
              <input
                id="password"
                type="password"
                placeholder="••••••••"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
            </div>

            <button type="submit" className="btn-login">
              Ingresar al Sistema
            </button>
          </form>

          <div className="form-footer">
            <a href="#">¿Olvidaste tu contraseña?</a>
            <p className="register-text">
              ¿No eres consultora? <a href="#">Regístrate aquí</a>
            </p>
          </div>

        </div>
      </div>

    </div>
  );
}