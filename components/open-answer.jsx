import React from "react";
import { connect } from "react-redux";

class OpenAnswer extends React.Component {
    render() {
        return null;
    }
}

export default connect(store => {
    return {
        questions: store.questions
    };
})(OpenAnswer);