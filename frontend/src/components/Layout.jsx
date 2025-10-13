import React from "react";
import Header from "./Header";
import Footer from "./Footer";
import { Outlet } from "react-router-dom";

const Layout = ({ children }) => {
  return (
    <div className="w-full h-screen flex flex-col justify-start">
      <Header />
      <main className="w-full flex-grow">
        <Outlet />
      </main>
      <Footer />
    </div>
  );
}

export default Layout;