import axios from "axios";

const Slides = async () => {
    const res = await axios.get(`http://localhost:8080/home/carousel`);

    return res.data
}
export {
    Slides
}
