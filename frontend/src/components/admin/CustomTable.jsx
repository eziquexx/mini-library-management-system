
const CustomTable = () => {
  return (
    <div className="flex flex-col justify-center">
      <table className="
          table-fixed border-collapse
          text-xs w-full h-full min-w-[1200px] "
        >
        <thead className="w-full">
          <tr className="w-full">
            <th className="w-1/22">
              <div className="bg-[#f2f3f7] py-2.5 mx-[2px]">id</div>
            </th>
            <th className="w-2/22">
              <div className="bg-[#f2f3f7] p-2.5 mx-[2px]">username</div>
            </th>
            <th className="w-2/22">
              <div className="bg-[#f2f3f7] py-2.5 mx-[2px]">password</div>
            </th>
            <th className="w-3/22">
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
              <div className="bg-[#f2f3f7] py-2.5 mx-[2px]">status</div>
            </th>
          </tr>
        </thead>
        <tbody>
          <tr className="border-b-1 border-gray-300">
            <td className="w-1/22 custom-scrollbar">
              <div className="px-2 py-4 mx-[2px]">1</div>
            </td>
            <td className="w-2/22 custom-scrollbar">
              <div className="px-2 py-4 mx-[2px]">user1</div>
            </td>
            <td className="w-2/22 custom-scrollbar">
              <div className="px-2 py-4 mx-[2px]">1234</div>
            </td>
            <td className="w-3/22 custom-scrollbar">
              <div className="px-2 py-4 mx-[2px] ">user1@example.com</div>
            </td>
            <td className="w-2/22 custom-scrollbar">
              <div className="px-2 py-4 mx-[2px]">ROLE_USER</div>
              </td>
            <td className="w-3/22 custom-scrollbar">
              <div className="px-2 py-4 mx-[2px]">2025-12-12</div>
              </td>
            <td className="w-3/22 custom-scrollbar">
              <div className="px-2 py-4 mx-[2px]">null</div>
              </td>
            <td className="w-3/22 custom-scrollbar">
              <div className="px-2 py-4 mx-[2px]">2025-12-12</div>
              </td>
            <td className="w-3/22 custom-scrollbar">
              <div className="px-2 py-4 mx-[2px]">2025-12-12</div>
              </td>
            <td className="w-3/22 custom-scrollbar">
              <div className="px-2 py-4 mx-[2px]">null</div>
              </td>
            <td className="w-2/22 custom-scrollbar">
              <div className="px-2 py-4 mx-[2px]">INACTIVE</div>
              </td>
          </tr>
          <tr className="border-b-1 border-gray-300">
            <td className="w-1/22 custom-scrollbar">
              <div className="px-2 py-4 mx-[2px]">1</div>
            </td>
            <td className="w-2/22 custom-scrollbar">
              <div className="px-2 py-4 mx-[2px]">user1</div>
            </td>
            <td className="w-2/22 custom-scrollbar">
              <div className="px-2 py-4 mx-[2px]">1234</div>
            </td>
            <td className="w-3/22 custom-scrollbar">
              <div className="px-2 py-4 mx-[2px] ">user1@example.com</div>
            </td>
            <td className="w-2/22 custom-scrollbar">
              <div className="px-2 py-4 mx-[2px]">ROLE_USER</div>
              </td>
            <td className="w-3/22 custom-scrollbar">
              <div className="px-2 py-4 mx-[2px]">2025-12-12</div>
              </td>
            <td className="w-3/22 custom-scrollbar">
              <div className="px-2 py-4 mx-[2px]">null</div>
              </td>
            <td className="w-3/22 custom-scrollbar">
              <div className="px-2 py-4 mx-[2px]">2025-12-12</div>
              </td>
            <td className="w-3/22 custom-scrollbar">
              <div className="px-2 py-4 mx-[2px]">2025-12-12</div>
              </td>
            <td className="w-3/22 custom-scrollbar">
              <div className="px-2 py-4 mx-[2px]">null</div>
              </td>
            <td className="w-2/22 custom-scrollbar">
              <div className="px-2 py-4 mx-[2px]">INACTIVE</div>
              </td>
          </tr>
        </tbody>
      </table>

      <div className="flex relative mt-4 h-10">
        <div className="flex items-center text-sm pl-3 text-gray-600">
          <span className="mr-1">1</span>
          <span className="mr-1">/</span>
          <span className="mr-1">10</span>
          <span>페이지</span>
        </div>
        {/* 페이징 */}
        <div className="absolute left-1/2 transform -translate-x-1/2 flex justify-center h-full">
          <div className="flex flex-row text-md h-full text-gray-500 ">
            <div className="p-2 mx-1 flex h-full items-center cursor-pointer hover:text-teal-600">
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-4">
                <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 19.5 8.25 12l7.5-7.5" />
              </svg>
            </div>
            <ul className="flex flex-row h-full justify-center items-center">
              <li className="px-2.5 py-1 mx-1 cursor-pointer bg-teal-600 text-white rounded-sm">1</li>
              <li className="px-2.5 py-1 mx-1 cursor-pointer hover:text-teal-600">2</li>
              <li className="px-2.5 py-1 mx-1 cursor-pointer hover:text-teal-600">3</li>
              <li className="px-2.5 py-1 mx-1 cursor-pointer hover:text-teal-600">4</li>
              <li className="px-2.5 py-1 mx-1 cursor-pointer hover:text-teal-600">...</li>
              <li className="px-2.5 py-1 mx-1 cursor-pointer hover:text-teal-600">9</li>
              <li className="px-2.5 py-1 mx-1 cursor-pointer hover:text-teal-600">10</li>
            </ul>
            <div className="p-2 mx-1 flex justify-center items-center cursor-pointer hover:text-teal-600">
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-4">
                <path stroke-linecap="round" stroke-linejoin="round" d="m8.25 4.5 7.5 7.5-7.5 7.5" />
              </svg>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default CustomTable;