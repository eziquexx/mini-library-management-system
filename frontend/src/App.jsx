import React from 'react'
import { Route, Routes } from 'react-router-dom'
import './App.css'
import LoginPage from './pages/LoginPage'
import HomePage from './pages/HomePage'
import SignupPage from './pages/SignupPage'
import NoticePage from './pages/NoticePage'
import SignupSuccessPage from './pages/SingupSuccessPage'

function App() {

  /**
   * HomePage: 메인
   * LoginPage: 로그인 페이지
   * SignupPage: 회원가입 페이지
   * NoticePage: 공지사항 페이지
   */
  return (
    <Routes>
      <Route path='/' element={<HomePage />}></Route>
      <Route path='/login' element={<LoginPage />}></Route>
      <Route path='/signup' element={<SignupPage />}></Route>
      <Route path='/notice' element={<NoticePage />}></Route>
      <Route path='/signup/success' element={<SignupSuccessPage />}></Route>
    </Routes>
  )
}

export default App
