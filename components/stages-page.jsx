import React from "react";
import { connect } from "react-redux";
import { withRouter } from "react-router-dom";
import {
    CircularProgress,
    GridList,
    RaisedButton
} from "material-ui";
import { addQuestions } from "../store/actions/questions";
import config from "../config.json";

class StagesPage extends React.Component {
    constructor(props) {
        super(props);

    }

    handleStageClick(stageId) {
        fetch(`${config.url}:${config.port}/Think-a-bit/game/stage?stageId=${stageId}&categoryId=${this.props.stages.selectedCategoryId}`, {
            method: "GET",
            credentials: "same-origin"
        }).then(response => {
            if (response.status === 401) {
                console.log("Session expored");
            } else if (response.status === 403) {
                console.log("Stage unavailable");
            } else if (response.status === 500) {
                console.log("Internal server error");
            } else if (response.status === 200) {
                return response.json();
            } else {
                console.log("Unknown error");
            }
        }).then(parsed => {
            console.log(parsed);
            this.props.addQuestions(parsed.questions);
            this.props.history.push("/question");
        }).catch(error => {
            console.log(error);
        });
    }

    render() {
        const styles = {
            gridList: {
                width: '100%',
                height: '100%',
                overflowY: 'auto',
            }
        };

        const stages = this.props.stages.stages.length > 0 ?
            <div>
                <h1>Stages for category {this.props.stages.category}</h1>
                <GridList
                    cellHeight={180}
                    cols={4}
                    padding={8}
                >
                    {this.props.stages.stages.map(stage => (
                        <RaisedButton
                            key={stage.id}
                            buttonStyle={{
                                "fontSize": "20px",
                                "textAlign": "center",
                                "margin": "auto"
                            }}
                            style={{
                                "height": "100%",
                                "width": "100%"
                            }}
                            onClick={() => this.handleStageClick.bind(this, stage.id)()}
                        >
                            {stage.id}
                        </RaisedButton>
                    ))}
                </GridList>
            </div> : <CircularProgress size={80} thickness={5} />;

            return <div style={styles.root}>
                {stages}
            </div>;
        }
    }

export default withRouter(connect(store => {
    return {
        stages: store.stages
    };
}, { addQuestions })(StagesPage));