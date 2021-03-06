import React from 'react'
import styled from 'styled-components';

import { FaBars } from 'react-icons/fa';

const Toggle = ({handleNavToggle}) => {
  return (
      <StyledToggle onClick={handleNavToggle}>
        <FaBars/>
      </StyledToggle>
  )
}

const StyledToggle = styled.button`
    top: 5%;
    right: 4%;
    color: #222;
    background: #eee;
    padding: .75rem;
    display: flex;
    place-items: center;
    font-size: 2rem;
    cursor: pointer;
`;

export default Toggle
