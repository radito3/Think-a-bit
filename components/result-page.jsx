import React from "react";
import { connect } from "react-redux";
import { Link, withRouter } from "react-router-dom";

class ResultPage extends React.Component {
    render() {
        console.log(this.props.result.questions);
        console.log(this.props.result.questions.length);
        if (this.props.result.questions.length > 0) {
            return <div>
                <h1>Oops! Questions you got wrong:</h1>
                {this.props.result.questions.map(question => {
                    return <p key={question}>{question}</p>;
                })}
                <Link to="/">Go back</Link>
            </div>;
        } else {
            return <div>
                <h1>It's all correct! Good job!</h1>
                <Link to="/">Go back</Link>
            </div>;
        }
    }
}

export default withRouter(connect(store => {
    return {
        result: store.result
    };
})(ResultPage));