import {Col, Row} from "react-bootstrap";
import React from "react";
import {APIService} from "../../Services/APIService";
import {PageCard} from "./Elements/PageCard";
import './Home.css'
import './Elements/CustomButtons.js'
import {BlogCreateButton} from "./Elements/CustomButtons";

class HomePage extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            homeData: []
        }
    }

    componentDidMount() {
        APIService.getAccessibleData()
            .then(response => {
                this.setState({homeData: response.data})
            }).catch(function (ex) {
            console.log('Response parsing failed. Error: ', ex);
            console.log('Response parsing failed. Error: Server Response', ex.response);
        });
    }

    render() {

        return (
            <>
                <div className='centered'>

                    <div>

                            <BlogCreateButton class='blogCreateButton'/>

                        {
                            <Row xs={1} md={2} className="g-4">
                                {Array.from({length: 1}).map((_, idx) => (
                                    this.state.homeData.map((blogData, index) => {
                                        return (
                                            <Col key={index}> {
                                                <PageCard blogData={blogData}/>
                                            }
                                            </Col>
                                        )
                                    })
                                ))}
                            </Row>
                        }
                    </div>
                </div>
            </>
        )
    }
}

export {
    HomePage
}