import React from "react";
import { connect } from "react-redux";
import { withRouter } from "react-router-dom";
import {
    CircularProgress,
    GridList,
    RaisedButton
} from "material-ui";
import { addStages, removeStages } from "../store/actions/stages";
import config from "../config.json";

class StagesPage extends React.Component {
    constructor(props) {
        super(props);

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
                            onClick={() => { }}
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
}, { addStages, removeStages })(StagesPage));