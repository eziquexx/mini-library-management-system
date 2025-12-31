import React, { useState } from "react";

const FocusPlaceholderInput = ({ type, value, id, name, placeholderText, onChange}) => {
  const [isFocused, setIsFocused] = useState(false);

  return (
    <>
      <input 
        type={type} 
        value={value}
        id={id} name={name} 
        onChange={onChange}
        onFocus={() => setIsFocused(true)}
        onBlur={() => setIsFocused(false)}
        placeholder={isFocused ? '' : placeholderText}
        className='
          col-span-3 
          py-3 px-3 
          border rounded-lg border-gray-300 
          text-sm leading-5 
          bg-white' 
      />
    </>
  );
}

export default FocusPlaceholderInput;