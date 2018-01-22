/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { Hochzeitclick11TestModule } from '../../../test.module';
import { StatisticMySuffixComponent } from '../../../../../../main/webapp/app/entities/statistic-my-suffix/statistic-my-suffix.component';
import { StatisticMySuffixService } from '../../../../../../main/webapp/app/entities/statistic-my-suffix/statistic-my-suffix.service';
import { StatisticMySuffix } from '../../../../../../main/webapp/app/entities/statistic-my-suffix/statistic-my-suffix.model';

describe('Component Tests', () => {

    describe('StatisticMySuffix Management Component', () => {
        let comp: StatisticMySuffixComponent;
        let fixture: ComponentFixture<StatisticMySuffixComponent>;
        let service: StatisticMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Hochzeitclick11TestModule],
                declarations: [StatisticMySuffixComponent],
                providers: [
                    StatisticMySuffixService
                ]
            })
            .overrideTemplate(StatisticMySuffixComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(StatisticMySuffixComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StatisticMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new StatisticMySuffix(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.statistics[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
