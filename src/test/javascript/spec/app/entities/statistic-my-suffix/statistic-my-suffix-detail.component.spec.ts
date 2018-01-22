/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { Hochzeitclick11TestModule } from '../../../test.module';
import { StatisticMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/statistic-my-suffix/statistic-my-suffix-detail.component';
import { StatisticMySuffixService } from '../../../../../../main/webapp/app/entities/statistic-my-suffix/statistic-my-suffix.service';
import { StatisticMySuffix } from '../../../../../../main/webapp/app/entities/statistic-my-suffix/statistic-my-suffix.model';

describe('Component Tests', () => {

    describe('StatisticMySuffix Management Detail Component', () => {
        let comp: StatisticMySuffixDetailComponent;
        let fixture: ComponentFixture<StatisticMySuffixDetailComponent>;
        let service: StatisticMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Hochzeitclick11TestModule],
                declarations: [StatisticMySuffixDetailComponent],
                providers: [
                    StatisticMySuffixService
                ]
            })
            .overrideTemplate(StatisticMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(StatisticMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StatisticMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new StatisticMySuffix(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.statistic).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
