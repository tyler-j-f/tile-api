import React from 'react'
import {Col, Row} from "react-bootstrap";
import PageSubHeader from "../styledComponents/PageSubHeader";
import StyledAnchor from "../styledComponents/styledAnchor";

const InfoList = () => {

  const WHITEPAPER_URL = "https://docs.google.com/document/d/15IjXOjpWq0J3otwjMkZxEIujxIoX2V_CYn3KaWDleSo/edit?usp=sharing";
  const TWITTER_URL = "https://twitter.com/Tile_NFT_io";
  const DISCORD_SERVER_URL = "https://discord.gg/3xuZpB3FDH";
  const LINKEDIN_URL = "https://www.linkedin.com/in/tylerjohnfitzgerald/";

  const getWhitepaperText = () => {
    return (
        <>
          <PageSubHeader>
            Whitepaper
          </PageSubHeader>
          <li>
            <StyledAnchor href={WHITEPAPER_URL} >Link to</StyledAnchor>&nbsp;Google doc.
          </li>
          <ul>
            <li>
              The whitepaper is still a work in progress (WIP).
            </li>
            <li>
              Please feel free to leave comments/questions directly on the google doc.
            </li>
          </ul>
        </>
    );
  }

  const getContactDetails = () => {
    return (
        <>
          <PageSubHeader>
            Contact Channels
          </PageSubHeader>
          <ul>
            <li>
              Email: tyler@tilenft.io
            </li>
            <li>
              Twitter: <StyledAnchor href={TWITTER_URL} >Link to</StyledAnchor>&nbsp;@Tile_NFT_io account
            </li>
            <li>
              Discord:
              <ul>
                <li>
                  <StyledAnchor href={DISCORD_SERVER_URL} >Link to</StyledAnchor>&nbsp;TileNFT Discord server
                </li>
                <li>
                  TileNFT Discord username: TileNFT#1210
                </li>
              </ul>
            </li>
          </ul>
        </>

    );
  }

  const getRepositoriesText = () => {
    return (
        <>
          <PageSubHeader>
            Code Repositories
          </PageSubHeader>
          <li>
            TileNFT Smart Contract Code (Solidity)
          </li>
          <li>
            TileNFT API/Frontend Code
          </li>
          <ul>
            <li>
              The API/frontend repo is currently private. If you would like to view the code, please contact me.
            </li>
          </ul>
        </>
    );
  }

  const getDeveloperDetails = () => {
    return (
        <>
          <PageSubHeader>
            Developers
          </PageSubHeader>
          <li>
            Tyler Fitzgerald
            <ul>
              <li>Linkedin: <StyledAnchor href={LINKEDIN_URL} >Link to</StyledAnchor>&nbsp;Tyler's LinkedIn account.</li>
              <li>This app is still a WIP, please reach out for any questions, concerns, or bugs.</li>
              <li>I am open to employment opportunities.</li>
              <li>Please reach out on LinkedIn (or one of the above contact channels), if you want Rinkeby Testnet ETH for testing this app. I can send you some.</li>
            </ul>
          </li>
        </>
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
        <Col xs={8} sm={8} md={8} lg={8} xl={8} >
          {getInfoTable()}
        </Col>
        <Col xs={2} sm={2} md={2} lg={2} xl={2} />
      </Row>
  )
}

export default InfoList
