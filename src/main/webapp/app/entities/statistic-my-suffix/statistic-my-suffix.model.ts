import { BaseEntity } from './../../shared';

export class StatisticMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public noOfFag?: number,
        public noOfGalleries?: number,
        public noOfInquiries?: number,
        public noOfLinkedProvider?: number,
        public noOfPortfolioImg?: number,
        public viewsTotalWP?: number,
        public profile?: BaseEntity,
    ) {
    }
}
