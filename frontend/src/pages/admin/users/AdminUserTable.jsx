import { useEffect, useState } from "react";


const AdminUserTable = ({onRowClick, postList, loading, error}) => {

  const [posts, setPosts] = useState([]);

  useEffect(() => {
    setPosts(postList);
  }, [postList]);

  let content;
  if (!loading && !error) {
    if (posts && posts.length > 0) {
      content = (
        <tbody>
          {posts.map((post) => (
            <tr 
              key={post.id} 
              className="border-b-1 border-gray-300 hover:bg-gray-200 cursor-pointer" 
              onClick={() => onRowClick(post.id)}
            >
              <td className="w-1/22 px-2 py-4 mx-[2px] overflow-hidden">
                <div className="custom-scrollbar">{post.id ? post.id : '-'}</div>
              </td>
              <td className="w-3/22 px-2 py-4 mx-[2px] overflow-hidden">
                <div className="custom-scrollbar">{post.username ? post.username : '-'}</div>
              </td>
              <td className="w-2/22 px-2 py-4 mx-[2px] overflow-hidden">
                <div className="custom-scrollbar">{post.email ? post.email : '-'}</div>
              </td>
              <td className="w-1/22 px-2 py-4 mx-[2px] overflow-hidden">
                <div className="custom-scrollbar">{post.role ? post.role : '-'}</div>
              </td>
              <td className="w-1/22 px-2 py-4 mx-[2px] overflow-hidden">
                <div className="custom-scrollbar text-center">{post.joinDate ? post.joinDate.split('T').join(' / ') : '-'}</div>
              </td>
              <td className="w-1/22 px-2 py-4 mx-[2px] overflow-hidden">
                <div className="custom-scrollbar text-center">{post.lastLoginDate ? post.lastLoginDate.split('T').join(' / ') : '-'}</div>
              </td>
              <td className="w-1/22 px-2 py-4 mx-[2px] overflow-hidden">
                <div className="custom-scrollbar text-center">{post.updatedAt ? post.updatedAt.split('T').join(' / ') : '-'}</div>
              </td>
              <td className="w-1/22 px-2 py-4 mx-[2px] overflow-hidden">
                <div className="custom-scrollbar text-center">{post.inactiveAt ? post.inactiveAt.split('T').join(' / ') : '-'}</div>
              </td>
              <td className="w-1/22 px-2 py-4 mx-[2px] overflow-hidden">
                <div className="custom-scrollbar text-center">{post.deletedAt ? post.deletedAt.split('T').join(' / ') : '-'}</div>
              </td>
              <td className="w-1/22 px-2 py-4 mx-[2px] overflow-hidden">
                <div className="custom-scrollbar text-center">{post.status ? post.status : '-'}</div>
              </td>
            </tr>
          ))}
        </tbody>
      );
    }
  } else {
    content = (
      <tbody>
        <tr>
          <td>등록된 게시물이 없습니다.</td>
        </tr>
      </tbody>
    )
  }

  return (
    <>
      <thead className="w-full">
        <tr className="w-full">
          <th className="w-1/22">
            <div className="bg-[#f2f3f7] py-2.5 mr-[2px]">id</div>
          </th>
          <th className="w-3/22">
            <div className="bg-[#f2f3f7] p-2.5 mx-[2px]">username</div>
          </th>
          <th className="w-4/22">
            <div className="bg-[#f2f3f7] py-2.5 mx-[2px] ">email</div>
          </th>
          <th className="w-2/22">
            <div className="bg-[#f2f3f7] py-2.5 mx-[2px]">role</div>
          </th>
          <th className="w-3/22">
            <div className="bg-[#f2f3f7] py-2.5 mx-[2px]">join_date</div>
          </th>
          <th className="w-3/22">
            <div className="bg-[#f2f3f7] py-2.5 mx-[2px]">last_login_date</div>
          </th>
          <th className="w-3/22">
            <div className="bg-[#f2f3f7] py-2.5 mx-[2px]">updated_at</div>
          </th>
          <th className="w-3/22">
            <div className="bg-[#f2f3f7] py-2.5 mx-[2px]">inactive_at</div>
          </th>
          <th className="w-3/22">
            <div className="bg-[#f2f3f7] py-2.5 mx-[2px]">deleted_at</div>
          </th>
          <th className="w-2/22">
            <div className="bg-[#f2f3f7] py-2.5 ml-[2px]">status</div>
          </th>
        </tr>
      </thead>
      
      {/* tbody */}
      { content }
    </>
  );
}

export default AdminUserTable;