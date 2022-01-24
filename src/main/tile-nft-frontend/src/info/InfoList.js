import React from 'react'
import {Col, Row} from "react-bootstrap";
import StyledText from "../styledComponents/StyledText";

const InfoList = () => {

  const getWhitepaperText = () => {
    return (
        <li>
          <StyledText>TileNFT Whitepaper</StyledText>
        </li>
    );
  }

  const getContactDetails = () => {
    return (
        <li>
          <StyledText>Contact Channels</StyledText>
          <ul>
            <li>
              <StyledText>Email: tyler@tilenft.io</StyledText>
            </li>
            <li>
              <StyledText>Discord Server: https://discord.gg/3xuZpB3FDH</StyledText>
            </li>
            <li>
              <StyledText>Discord User: TileNFT#1210</StyledText>
            </li>
            <li>
              <StyledText>Twitter: @Tile_NFT_io</StyledText>
            </li>
          </ul>
        </li>
    );
  }

  const getRepositoriesText = () => {
    return (
        <li>
          <StyledText>Code Repositories</StyledText>
          <ul>
            <li>
              <StyledText>TileNFT Smart Contract Code (Solidity)</StyledText>
            </li>
            <li>
              <StyledText>TileNFT API/Frontend Code</StyledText>
            </li>
            <ul>
              <li>
                <StyledText>The API/frontend repo is currently private. If you would like to view the code, please contact me.</StyledText>
              </li>
            </ul>
          </ul>
        </li>
    );
  }

  const getDeveloperDetails = () => {
    return (
        <li>
          <StyledText>Developers</StyledText>
          <ul>
            <li>
              <StyledText>Tyler Fitzgerald</StyledText>
              <ul>
                <li>LinkedIn</li>
              </ul>
            </li>
          </ul>
        </li>
    );
  }

  const getInfoTable = () => {
    return (
        <ul>
          {getWhitepaperText()}
          {getRepositoriesText()}
          {getContactDetails()}
          {getDeveloperDetails()}
        </ul>
    );
  }

  return (
      <Row>
        <Col xs={2} sm={2} md={2} lg={2} xl={2} />
        <Col xs={8} sm={8} md={8} lg={8} xl={8} className="text-center" >
          {getInfoTable()}
        </Col>
        <Col xs={2} sm={2} md={2} lg={2} xl={2} />
      </Row>
  )
}

export default InfoList
