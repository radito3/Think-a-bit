import React from "react";
import { connect } from "react-redux";
import { withRouter } from "react-router-dom";
import {
    CircularProgress,
    GridList,
    RaisedButton
} from "material-ui";
import Lock from "material-ui/svg-icons/action/lock";
import { addQuestions } from "../store/actions/questions";
import { setSessionExpired } from "../store/actions/authentication";
import { decrementAvailableAfter } from "../store/actions/stages";
import config from "../config.json";

class StagesPage extends React.Component {
    constructor(props) {
        super(props);

        this.interval = setInterval(() => this.props.decrementAvailableAfter(), 1000);
    }

    componentWillUnmount() {
        clearInterval(this.interval);
    }

    handleStageClick(stageId) {
        fetch(`${config.url}:${config.port}/Think-a-bit/game/stage?stageId=${stageId}&categoryId=${this.props.stages.selectedCategoryId}`, {
            method: "GET",
            credentials: "same-origin"
        }).then(response => {
            if (response.status === 401) {
                console.log("Session expired");
                this.props.setSessionExpired(true);
            } else if (response.status === 403) {
                console.log("Stage unavailable");
            } else if (response.status === 200) {
                return response.json();
            } else {
                console.log("Unknown error");
            }
        }).then(parsed => {
            console.log(parsed);
            this.props.addQuestions(parsed.questions, stageId);
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
                            disabled={!stage.isReached || stage.availableAfter > 0}
                        >
                            {stage.stageNumber}
                            <br />
                            {stage.availableAfter > 0 && `Available after ${stage.availableAfter} seconds`}
                            <br />
                            {!stage.isReached && <Lock />}
                            <br />
                            {!stage.isReached && "Complete all previous stages to unlock this one"}
                            <br />
                            {stage.isReached && `${stage.attempts} attempts left`}
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
}, { addQuestions, setSessionExpired, decrementAvailableAfter })(StagesPage));