import React from 'react'
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import { FaTimes } from 'react-icons/fa';

const Menu = ({handleNavToggle}) => {
  return (
      <StyledMenu>
        <StyledLink onClick={handleNavToggle} to="/">Home</StyledLink>
        <StyledLink onClick={handleNavToggle} to="/leaderboard">Leaderboard</StyledLink>
        <StyledLink onClick={handleNavToggle} to="/view">View Your TileNFT</StyledLink>
        <StyledLink onClick={handleNavToggle} to="/openSea">Buy On OpenSea</StyledLink>
        <StyledLink onClick={handleNavToggle} to="/update">Update Your TileNFT</StyledLink>
        <StyledLink onClick={handleNavToggle} to="/merge">Merge Your TileNFTs</StyledLink>
        <StyledLink onClick={handleNavToggle} to="/info">Info</StyledLink>
        <CloseToggle onClick={handleNavToggle}><FaTimes/></CloseToggle>
      </StyledMenu>
  )
}

const StyledMenu = styled.div`
    position: fixed;
    top: 0;
    right: 0;
    height: 100vh;
    width: 100%;
    @media screen and (min-width: 790px) {
        width: 60%;
    }
    background-color: rgb(58, 66, 81, .9);
    z-index: 99;

    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
`;

const StyledLink = styled(Link)`
    color: #eee;
    text-decoration: none;
    font-size: clamp(3rem, 4vw, 6vw);
    font-family: Impact, Haettenschweiler, 'Arial Narrow Bold', sans-serif;
    transition: .2s all ease-in-out;

    user-select: none; /* supported by Chrome and Opera */
   -webkit-user-select: none; /* Safari */
   -khtml-user-select: none; /* Konqueror HTML */
   -moz-user-select: none; /* Firefox */
   -ms-user-select: none; /* Internet Explorer/Edge */

    &:hover {
        transition: .2s all ease-in-out;
        color: orangered;
    }
`;

const CloseToggle = styled.button`
    position: fixed;
    margin-top: 50px;
    top: 5%;
    right: 4%;
    background: #222;
    color: #fff;
    padding: .75rem;
    display: flex;
    place-items: center;
    font-size: 2rem;
    cursor: pointer;
`;

export default Menu
