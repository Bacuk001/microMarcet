import {Injectable} from '@angular/core';
import {Headers, Http} from '@angular/http';
import {IMarketService} from './imarket.service';
import {Market} from '../../entities/merket';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';

const URL = '/CityMarket/api/market';
const CONTENT_TYPE_VALUE = 'application/json;charset=utf-8';
const MARKET_NOT_SAVE = 'Market do not save!';
const SAVE_SUCCESS = 'The saving was successful!';
const NOT_DOWNLOAD = 'Error download!';

/**
 * The class describes methods for working with markets.
 */
@Injectable()
export class MarketService implements IMarketService {
  private markets: Market[];
  private selectMarket;

  constructor(private http: Http) {
  }

  /**
   * The method generates a query to obtain a list of markets created in the application.
   */
  getPromiseMarkets() {
    return new Promise((resolve, reject) => {
      this.http.get(URL + 's').subscribe((resp) => {
          this.markets = resp.json();
          resolve(this.markets);
        },
        () => reject(NOT_DOWNLOAD));
    });
  }

  /**
   * The method returns the store that was selected for work.
   */
  public getSelectMarket() {
    return this.selectMarket;
  }

  /**
   * The method establishes a store with which the products will be taken off.
   */
  public setSelectMarket(market: Market) {
    this.selectMarket = market;
  }

  /**
   * The method generates a request to store the store.
   */
  saveMarket(market: Market) {
    const body = JSON.stringify(market);
    const headers = new Headers({'Content-Type': CONTENT_TYPE_VALUE});
    return new Promise((resolve, reject) => {
      this.http.post(URL + '/save', body, {headers: headers})
        .subscribe(() => resolve(SAVE_SUCCESS),
          () => reject(MARKET_NOT_SAVE));
    });
  }
}
