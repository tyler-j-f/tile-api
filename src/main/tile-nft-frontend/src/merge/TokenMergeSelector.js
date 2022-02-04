import React from 'react'
import styled from 'styled-components';
import ViewToken from "../view/ViewToken";
import StyledText from "../styledComponents/StyledText";

const noop = () => {};

const TokenMergeSelector = ({
  tokenLoadedCallback = noop,
  headerText = ''
}) => {
  return (
      <>
        <StyledText>{headerText}</StyledText>
        <ViewToken
            tokenLoadedCallback={tokenLoadedCallback}
        />
      </>
  )
}

export default TokenMergeSelector
