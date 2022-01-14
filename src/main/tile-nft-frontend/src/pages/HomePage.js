import React, {useEffect} from 'react'
import styled from 'styled-components';
import {useNavigate} from "react-router-dom";

const HomePage = () => {

  const navigate = useNavigate();

  useEffect(() => {
    let tokenId = new URL(window.location.href).searchParams.get('tokenId');
    if (tokenId) {
      navigate("/view");
    }
  }, []);

  return (
      <StyledHomePage>
        <Heading className="animate__animated animate__fadeInLeft">TileNFT</Heading>
      </StyledHomePage>
  )
}

const StyledHomePage = styled.div`
    min-height: 50vh;
    width: 100vw;
    background-color: #282c34;

    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
`;

const Heading = styled.h1`
    font-size: clamp(3rem, 5vw, 7vw);
    color: #eee;
    font-weight: 700;
    margin: 0;
    padding: 0;

    user-select: none; /* supported by Chrome and Opera */
   -webkit-user-select: none; /* Safari */
   -khtml-user-select: none; /* Konqueror HTML */
   -moz-user-select: none; /* Firefox */
   -ms-user-select: none; /* Internet Explorer/Edge */
`;

export default HomePage
