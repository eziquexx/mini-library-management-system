import React from 'react'
import { Route, Routes } from 'react-router-dom'
import './App.css'
import LoginPage from './pages/LoginPage'
import HomePage from './pages/HomePage'
import SignupPage from './pages/SignupPage'

function App() {

  return (
    <Routes>
      <Route path='/' element={<HomePage />}></Route>
      <Route path='/login' element={<LoginPage />}></Route>
      <Route path='/signup' element={<SignupPage />}></Route>
    </Routes>
  )
}

export default App
