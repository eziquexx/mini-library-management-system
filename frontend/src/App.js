import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import HomePage from './pages/HomePage';
import LoginPage from './pages/LoginPage';
import LoginSuccessPage from './pages/LoginSuccessPage';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/login" element={<LoginPage />}/>
        <Route path="/login/success" element={<LoginSuccessPage />}/>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
