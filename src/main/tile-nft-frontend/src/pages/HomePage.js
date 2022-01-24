import React, {useEffect} from 'react'
import styled from 'styled-components';
import {useNavigate} from "react-router-dom";
import StyledPage from "../styledComponents/StyledPage";
import {DropdownButton, Dropdown} from "react-bootstrap";

const HomePage = () => {

  const navigate = useNavigate();

  useEffect(() => {
    let tokenId = new URL(window.location.href).searchParams.get('tokenId');
    if (tokenId) {
      navigate("/view?tokenId=" + tokenId);
    }
  }, []);

  const getImageUrl = () => `${window.location.origin}/api/image/getLogo`;

  const getNetworkSelectDropdown = () => {
    return (
        <DropdownButton title="Select ETH Network" menuVariant={'warning'}>
          <Dropdown.Item href="#/rinkeby" active >Testnet (Rinkeby)</Dropdown.Item>
          <Dropdown.Item href="#/mainnet" disabled >Mainnet (Not deployed on mainnet at this moment.)</Dropdown.Item>
        </DropdownButton>
    );
  }

  return (
      <StyledPage>
        {getNetworkSelectDropdown()}
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
