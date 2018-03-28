import React from "react";
import { Link, withRouter } from "react-router-dom";

class IndexPage extends React.Component {
    render() {
        return <div>
            <p>IndexPage</p>
            <Link to="/question">Question</Link>
        </div>;
    }
}

export default withRouter(IndexPage);