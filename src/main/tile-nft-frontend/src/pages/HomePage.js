import React, {useEffect, useState} from 'react'
import styled from 'styled-components';
import {useNavigate} from "react-router-dom";
import StyledPage from "../styledComponents/StyledPage";
import {DropdownButton, Dropdown} from "react-bootstrap";

const HomePage = () => {

  const navigate = useNavigate();

  const MAINNET_NETWORK_ID = 0;
  const RINKEBY_NETWORK_ID = 1;
  const NETWORKS_MAP = ["Mainnet", "Testnet, Rinkeby"];
  const [activeNetworkId, setActiveNetworkId] = useState(RINKEBY_NETWORK_ID);

  useEffect(() => {
    let tokenId = new URL(window.location.href).searchParams.get('tokenId');
    if (tokenId) {
      navigate("/view?tokenId=" + tokenId);
    }
  }, []);

  const getImageUrl = () => `${window.location.origin}/api/image/getLogo`;

  const onNetworkSelect = (id) => setActiveNetworkId(id);

  const getNetworkSelectDropdown = () => {
    return (
        <DropdownButton title="Select ETH Network" className="styledDropdown" >
          <Dropdown.Item
              onClick={() => onNetworkSelect(RINKEBY_NETWORK_ID)}
              active={activeNetworkId === RINKEBY_NETWORK_ID}
          >
            Testnet (Rinkeby)
          </Dropdown.Item>
          <Dropdown.Item
              onClick={() => onNetworkSelect(MAINNET_NETWORK_ID)}
              active={activeNetworkId === MAINNET_NETWORK_ID}
              disabled
          >
            Mainnet (Not deployed on mainnet at this moment.)
          </Dropdown.Item>
        </DropdownButton>
    );
  }

  const getActiveNetwork = () =>
      <p>Active Network:&nbsp;<b>{getActiveNetworkName()}</b></p>

  const getActiveNetworkName = () => NETWORKS_MAP[activeNetworkId];

  return (
      <StyledPage>
        {getNetworkSelectDropdown()}
        {getActiveNetwork()}
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
    margin-top: 50px;
    margin-bottom: 100px;
    display: block;
    width: 350px;
    height: 428px;
    `;

export default HomePage
