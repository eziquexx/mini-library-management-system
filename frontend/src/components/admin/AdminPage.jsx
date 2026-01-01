import { Outlet } from "react-router-dom";
import SidebarAdmin from "./SidebarAdmin";


const AdminPage = () => {
  return (
    <div className="w-full min-h-screen flex">
      <SidebarAdmin />
      <main className="grow-9 bg-[#f9fafc]">
        <Outlet />
      </main>
    </div>
  );
}

export default AdminPage;