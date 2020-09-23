import { Moment } from 'moment';

export interface ICategories {
  id?: number;
  categoryName?: string;
  description?: string;
  categoryImageContentType?: string;
  categoryImage?: any;
  isPublic?: boolean;
  user?: string;
  creationDate?: Moment;
}

export class Categories implements ICategories {
  constructor(
    public id?: number,
    public categoryName?: string,
    public description?: string,
    public categoryImageContentType?: string,
    public categoryImage?: any,
    public isPublic?: boolean,
    public user?: string,
    public creationDate?: Moment
  ) {
    this.isPublic = this.isPublic || false;
  }
}
