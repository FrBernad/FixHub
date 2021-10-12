import {DiscoverService} from "./discover.service";
import {getTestBed, TestBed} from "@angular/core/testing";
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {environment} from "../../environments/environment";
import {HttpStatusCode} from "@angular/common/http";
import {RouterTestingModule} from "@angular/router/testing";

describe('DiscoverService', () => {
  let injector: TestBed;
  let service: DiscoverService;
  let httpMock: HttpTestingController;


  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule,
                RouterTestingModule],
      providers: [DiscoverService],
    });

    injector = getTestBed();
    service = injector.inject(DiscoverService);
    httpMock = injector.inject(HttpTestingController);
  });

   it('getCategories() should return categories', () => {
    let categories = {
      data: ['CARPINTERO', 'CATERING', 'CHEF', 'ELECTRICISTA',
        'ENTREGA', 'FOTOGRAFO', 'FUMIGADOR', 'GASISTA',
        'HERRERO', 'JARDINERO', 'LIMPIEZA', 'CUIDADOR_DE_ANCIANO',
        'MANTENIMIENTO', 'MECANICO', 'MUDANZA', 'NINERA',
        'PASEADOR_DE_PERRO', 'PLOMERO', 'PINTOR', 'TECHISTA', 'VIDRIERO'],
    };

    service.getCategories().subscribe((res: {}) => {
      return expect(res).toEqual(categories);
    });

    const req = httpMock.expectOne(environment.apiBaseUrl + '/jobs/categories');
    expect(req.request.method).toBe('GET');
    req.flush(categories);
  });


  it('getJobs(jp: JobPaginationQuery) should return jobs', () => {
    let results = {
      page : 0,
      totalPages : 1,
      results : []
    };

    service.getJobs({page: 0});

    const req = httpMock.expectOne(environment.apiBaseUrl + '/jobs?page=0');
    expect(req.request.method).toBe('GET');
    req.flush(results, {status: HttpStatusCode.Ok, statusText: HttpStatusCode.Ok.toString()});
  });


  it('getStates() should return states', () => {
    let states = [{name: 'Buenos Aires', id: 1}, {name: 'CABA', id: 2}, {name: 'Misiones', id: 3}];

    service.getStates().subscribe((res) => {
      expect(res).toEqual(states);
    });

    const req = httpMock.expectOne(environment.apiBaseUrl + '/locations/states');
    expect(req.request.method).toBe('GET');
    req.flush(states);
  });

  it('getStateCities(id: string) should return cities', () => {
    let id = '1';
    let cities = [{name: 'Adrogue', id: 1}, {name: 'Burzaco', id: 2}, {name: 'Lomas de Zamora', id: 3}];
    service.getStateCities(id).subscribe((res) => {
      expect(res).toEqual(cities);
    });

    const req = httpMock.expectOne(environment.apiBaseUrl + '/locations/states/' + id + '/cities');
    expect(req.request.method).toBe('GET');
    req.flush(cities);
  });

  afterEach(() => {
    httpMock.verify();
  });

});
