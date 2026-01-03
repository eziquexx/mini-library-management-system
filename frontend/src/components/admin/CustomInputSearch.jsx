import { useState } from "react";

const CustomInputSearch = () => {
  const [keyword, setKeyword] = useState("");
  const [isFocused, setIsFocused] = useState(false);

  const handleInputChange = (e) => {
    setKeyword(e.target.value);
  }

  // input에 focus시 활성화
  const handleFocus = () => {
    setIsFocused(true);
  }

  // input에 focus 아웃되었을때 비활성화
  const handleBlur = () => {
    setIsFocused(false);
  }

  // 버튼 클릭 시 keyword 값
  const search = () => {
    setIsFocused(false); // focus 해제
    console.log(keyword); // 입력한 keyword 출력 테스트
  }

  return (
    <div className={`w-[320px] h-[42px] flex flex-row ml-2 text-sm
      border border-gray-300 rounded-md 
      ${isFocused ? 'focus-within:border-teal-500' : ''}`}>
      <input 
        type="text" 
        id="usrkwd" 
        name="usrkwd"
        className="
          w-4/5 h-full p-3
          outline-none
        "
        value={keyword}
        onChange={handleInputChange}
        onFocus={handleFocus}
        onBlur={handleBlur}
      />
      <button 
        className="
          w-auto h-full px-3
          text-gray-400
          cursor-pointer
          ml-auto
        "
        onClick={search}
      >
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-5">
          <path stroke-linecap="round" stroke-linejoin="round" d="m21 21-5.197-5.197m0 0A7.5 7.5 0 1 0 5.196 5.196a7.5 7.5 0 0 0 10.607 10.607Z" />
        </svg>
      </button>
    </div>
  );
}

export default CustomInputSearch;