import MergeResultDetailsList from "./MergeResultDetailsList";
import MergeResultDetailsTable from "./MergeResultDetailsTable";
import PageSubHeader from "../styledComponents/PageSubHeader";

const MergeResultDetails = ({}) => (
    <>
      <PageSubHeader>Merge Output Explanation</PageSubHeader>
      <MergeResultDetailsList />
      <MergeResultDetailsTable />
    </>
)

export default MergeResultDetails;
