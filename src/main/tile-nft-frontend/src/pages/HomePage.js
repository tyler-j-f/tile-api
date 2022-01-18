import React, {useEffect} from 'react'
import styled from 'styled-components';
import {useNavigate} from "react-router-dom";
import StyledPage from "../styledComponents/StyledPage";

const HomePage = () => {

  const navigate = useNavigate();

  useEffect(() => {
    let tokenId = new URL(window.location.href).searchParams.get('tokenId');
    if (tokenId) {
      navigate("/view?tokenId=" + tokenId);
    }
  }, []);

  const getImageUrl = () => `${window.location.origin}/api/image/getLogo`;

  return (
      <StyledPage>
        <StyledImg
            src={getImageUrl()}
            alt={"TileNFT"}
        />
      </StyledPage>
  )
}

const StyledImg =
    styled.img.attrs(props => props)`
    margin: 10px;
    margin-top: 100px;
    margin-bottom: 100px;
    display: block;
    width: 350px;
    height: 428px;
    `;

export default HomePage
