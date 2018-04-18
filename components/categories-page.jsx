import React from "react";
import { Redirect, withRouter } from "react-router-dom";
import { connect } from "react-redux";
import {
    CircularProgress,
    GridList,
    RaisedButton
} from "material-ui";
import StarBorder from "material-ui/svg-icons/toggle/star-border";
import { selectCategory, addStages } from "../store/actions/stages";
import { setSessionExpired } from "../store/actions/authentication";
import config from "../config";

class CategoriesPage extends React.Component {
    constructor(props) {
        super(props);
    }

    handleCategoryClick(categoryId) {
        fetch(`${config.url}:${config.port}/Think-a-bit/game/category/${categoryId}`, {
            method: "GET",
            credentials: "same-origin"
        }).then(response => {
            if (response.status === 401) {
                this.props.setSessionExpired(true);
            } else if (response.status === 200) {
                return response.json();
            } else {
                console.log("Unknown error");
                console.log(response.status);
            }
        }).then(parsed => {
            console.log(parsed);
            this.props.addStages(parsed.stages, parsed.name);
            this.props.selectCategory(categoryId);
            this.props.history.push("/stages");
        }).catch(error => {
            console.log(error);
        });
    }

    render() {
        if (!this.props.authentication.username) {
            return <Redirect to="/login" />;
        }

        const styles = {
            gridList: {
                width: '100%',
                height: '100%',
                overflowY: 'auto',
            }
        };

        const categories = this.props.categories.length > 0 ?
            <div>
                <h1>Select a category</h1>
                <GridList
                    cellHeight={180}
                    cols={4}
                    padding={8}
                >
                    {this.props.categories.map(category => (
                        <RaisedButton
                            key={category.id}
                            buttonStyle={{
                                "fontSize": "20px",
                                "textAlign": "center",
                                "margin": "auto"
                            }}
                            style={{
                                "height": "100%",
                                "width": "100%"
                            }}
                            onClick={() => this.handleCategoryClick.bind(this, category.id)()}
                        >{category.name}
                        </RaisedButton>
                    ))}
                </GridList>
            </div> :
            <CircularProgress size={80} thickness={5} />;

        return <div style={styles.root}>
            {categories}
        </div>;
    }
}

export default withRouter(connect(store => {
    return {
        categories: store.categories.categories,
        authentication: store.authentication
    };
}, { selectCategory, addStages, setSessionExpired })(CategoriesPage));