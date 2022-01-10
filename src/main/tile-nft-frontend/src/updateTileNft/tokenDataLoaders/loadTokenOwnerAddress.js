
export const loadTokenOwnerAddress = ({tokenId, setDataToUpdateRelatedData}) => {
  return fetch(
      `http://localhost:8080/api/contract/getOwnerAddress/${tokenId}`,
      {method: 'get'}
  )
  .then(response => {
    if (response.status === 200) {
      return response.json();
    }
    return null;
  })
  .then(ownerAddress => {
    if (ownerAddress === null) {
      let errorMessage = 'Token owner address is null';
      console.log(errorMessage);
      throw errorMessage;
    }
    return ownerAddress;
  })
  .catch(err => {
    console.log("Error caught!!!", err);
  });
}

export default loadTokenOwnerAddress;
