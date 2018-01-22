/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { Hochzeitclick11TestModule } from '../../../test.module';
import { DataImportMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/data-import-my-suffix/data-import-my-suffix-detail.component';
import { DataImportMySuffixService } from '../../../../../../main/webapp/app/entities/data-import-my-suffix/data-import-my-suffix.service';
import { DataImportMySuffix } from '../../../../../../main/webapp/app/entities/data-import-my-suffix/data-import-my-suffix.model';

describe('Component Tests', () => {

    describe('DataImportMySuffix Management Detail Component', () => {
        let comp: DataImportMySuffixDetailComponent;
        let fixture: ComponentFixture<DataImportMySuffixDetailComponent>;
        let service: DataImportMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Hochzeitclick11TestModule],
                declarations: [DataImportMySuffixDetailComponent],
                providers: [
                    DataImportMySuffixService
                ]
            })
            .overrideTemplate(DataImportMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DataImportMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DataImportMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new DataImportMySuffix(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.dataImport).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
