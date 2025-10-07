import React from 'react'
import { Route, Routes } from 'react-router-dom'
import './App.css'
import LoginPage from './pages/LoginPage'
import HomePage from './pages/HomePage'
import SignupPage from './pages/SignupPage'
import NoticePage from './pages/NoticePage'
import SignupSuccessPage from './pages/SingupSuccessPage'
import Layout from './components/Layout'

function App() {

  /**
   * HomePage: 메인
   * LoginPage: 로그인 페이지
   * SignupPage: 회원가입 페이지
   * NoticePage: 공지사항 페이지
   */
  return (
    <Routes>
      <Route element={<Layout />}>
        <Route path='/' element={<HomePage />} />
        <Route path='/login' element={<LoginPage />} />
        <Route path='/signup' element={<SignupPage />} />
        <Route path='/notice' element={<NoticePage />} />
        <Route path='/signup/success' element={<SignupSuccessPage />} />
      </Route>
    </Routes>
  )
}

export default App
