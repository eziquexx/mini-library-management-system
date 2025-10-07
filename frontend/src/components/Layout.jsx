import React from "react";
import Header from "./Header";
import Footer from "./Footer";
import { Outlet } from "react-router-dom";

const Layout = ({ children }) => {
  return (
    <div className="w-full">
      <Header />
      <main className="">
        <Outlet />
      </main>
      <Footer />
    </div>
  );
}

export default Layout;