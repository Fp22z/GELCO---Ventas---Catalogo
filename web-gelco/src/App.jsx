import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Home from './UI/Pages/Home';
import Login from './UI/Pages/Login';

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
      </Routes>
    </BrowserRouter>
  );
}