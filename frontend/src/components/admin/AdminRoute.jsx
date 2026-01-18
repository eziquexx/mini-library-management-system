import { useEffect } from "react";
import useUserStore from "../../stores/useUserStore";
import { Navigate, Outlet } from "react-router-dom";

const AdminRoute = () => {
  const { user, loading, fetchUser } = useUserStore();

  useEffect(() => {
    if (!user && loading) {
      fetchUser();
    }
  }, [user, loading]);

  if (loading) return <div>로딩 중...</div>;

  // 로그인 안 되어 있거나 role이 admin이 아닌 경우
  if (!user || (user.role !== 'ROLE_ADMIN' && user.role !== 'ROLE_MANAGER')) {
    return <Navigate replace to="/admin/login" />;
  }

  // 관리자라면 하위 라우트 렌더링
  return <Outlet />;
};

export default AdminRoute;
