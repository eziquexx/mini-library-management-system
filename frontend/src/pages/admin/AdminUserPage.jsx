import AdminHeader from "../../components/admin/AdminHeader";
import CustomSelect from "../../components/admin/CustomSelect";


const AdminUserPage = () => {
  const title = ["회원관리"];
  const defaultOption = "사용자 ID";
  const options = ["사용자 ID", "이메일"];

  return (
    <div>
      <AdminHeader title={title} />

      {/* 컨텐츠 */}
      <div className="bg-white p-4 rounded-xl shadow-[0px_0px_14px_rgba(0,0,0,0.08)]">
        <h4 className="text-lg font-bold">회원목록</h4>
        <div className="flex flex-row mt-5.5">
          <CustomSelect options={options} defaultOption={defaultOption} />
          <div>
            <input 
              type="text" 
              id="usrkwd" 
              name="usrkwd"
              className="border"
            />
            <button 
              className="border"
            >버튼</button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default AdminUserPage;