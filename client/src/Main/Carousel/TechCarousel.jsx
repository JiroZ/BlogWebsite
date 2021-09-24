import Carousel from 'react-responsive-carousel'
import axios from "axios";


const TechCarousel = async () => {
    const res = await axios.get(`http://localhost:8080/home/carousel`);

    return (
        {
        //     Slides.map(data => {
        //         return (
        //             <>
        //                 <Carousel>
        //                     <div>
        //                         <img src="assets/1.jpeg"/>
        //                         <p className="legend">Legend 1</p>
        //                     </div>
        //                 </Carousel>
        //             </>
        //         )
        //     })
        //
        }
    )
}

export default TechCarousel;