import React from 'react'
import styled from 'styled-components';
import ViewToken from "../view/ViewToken";

const MergePage = () => {
  return (
      <StyledPage>
        <Heading className="animate__animated animate__fadeInLeft">Merge A TileNft</Heading>
        <StyledText>Select first tile to merge.</StyledText>
        <ViewToken />
        <StyledText>Select second tile to merge.</StyledText>
        <ViewToken />
      </StyledPage>
  )
}

const StyledPage = styled.div`
    min-height: 100vh;
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

const StyledText =
    styled.p`
    color: white;
    font-weight: bold;
    `;

export default MergePage
