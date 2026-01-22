import React from 'react'
import { Route, Routes } from 'react-router-dom'
import './App.css'
import LoginPage from './pages/users/LoginPage'
import HomePage from './pages/users/HomePage'
import SignupPage from './pages/users/SignupPage'
import NoticePage from './pages/users/NoticePage'
import SignupSuccessPage from './pages/users/SingupSuccessPage'
import Layout from './components/users/Layout'
import NoticeDetailPage from './pages/users/NoticeDetailPage'
import BookDetailPage from './pages/users/BookDetailPage'
import BookListPage from './pages/users/BookListPage'
import MyPage from './pages/users/MyPage'
import AdminRoute from './components/admin/AdminRoute'
import AdminDashboardPage from './pages/admin/dashboard/AdminDashboardPage'
import AdminUserPage from './pages/admin/users/AdminUserPage'
import AdminBookPage from './pages/admin/book/AdminBookPage'
import AdminLoanPage from './pages/admin/loan/AdminLoanPage'
import AdminReviewPage from './pages/admin/review/AdminReviewPage'
import AdminNoticePage from './pages/admin/notice/AdminNoticePage'
import AdminLoginPage from './pages/admin/auth/AdminLoginPage'
import AdminPage from './components/admin/AdminPage'

function App() {

  return (
    <Routes>
      {/* Users Layout */}
      <Route element={<Layout />}>
        <Route path='/' element={<HomePage />} />
        <Route path='/login' element={<LoginPage />} />
        <Route path='/signup' element={<SignupPage />} />
        <Route path='/signup/success' element={<SignupSuccessPage />} />
        <Route path='/mypage' element={<MyPage />} />
        <Route path='/notice' element={<NoticePage />} />
        <Route path='/notice/:noticeId' element={<NoticeDetailPage />}/>
        <Route path='/books' element={<BookListPage />}/>
        <Route path='/books/:bookId' element={<BookDetailPage />}/>
      </Route>
      <Route>
        <Route path="/admin/login" element={<AdminLoginPage />} />
        <Route element={<AdminRoute />}>
          <Route element={<AdminPage />}>
            <Route path="/admin" element={<AdminDashboardPage />} />
            <Route path="/admin/dashboard" element={<AdminDashboardPage />} />
            <Route path="/admin/users" element={<AdminUserPage />} />
            <Route path="/admin/books" element={<AdminBookPage />} />
            <Route path="/admin/loans" element={<AdminLoanPage />} />
            <Route path="/admin/reviews" element={<AdminReviewPage />} />
            <Route path="/admin/notices" element={<AdminNoticePage />} />
          </Route>
        </Route>
      </Route>
    </Routes>
  )
}

export default App
