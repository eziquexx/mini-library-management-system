import AdminHeader from "../../../components/admin/AdminHeader";


const AdminBookPage = () => {
  const title = ["도서관리"];

  return (
    <div>
      <AdminHeader title={title} />
      <div className="bg-white p-5 rounded-xl shadow-[0px_0px_14px_rgba(0,0,0,0.08)]">
        <h1 className="text-xl text-center my-20">준비중입니다.</h1>
      </div>
    </div>
  );
}

export default AdminBookPage;